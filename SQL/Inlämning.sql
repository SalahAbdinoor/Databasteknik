-- -------------------------------------------------------------------------------------------------------------------------------------------------------- --
-- ---------------------------------------------------------------------G KRAVEN--------------------------------------------------------------------------- --
-- -------------------------------------------------------------------------------------------------------------------------------------------------------- --

-- Om beställningen inte finns eller om vi skickar in null som beställningsid ska en ny beställning skapas och produkten läggas till i den.

DROP PROCEDURE IF EXISTS AddToCart;
DELIMITER $$
CREATE PROCEDURE AddToCart(in kundid int, in köpnr int,in produktid int)
BEGIN

DECLARE maxnumber int default 0;

DECLARE EXIT HANDLER FOR 1062
BEGIN 
RESIGNAL SET message_text = 'Ett fel uppstod';
ROLLBACK;
END;

-- Hämtar ut max nummer: select max(köpnr) from beställning

SET maxnumber = findmaxnumber();
SET autocommit = 0;

START TRANSACTION;

-- Om lagret inte är slut, minska produkterna i lagret.

if 0 < (Select lager from produkt where produktid = produkt.id group by id) then 
update produkt
set lager = lager -1
where produkt.id = produktid;


-- om köpnumret motsvarar ett som existerar, lägg till i samma köp.
if köpnr = (Select köpnr from beställning where beställning.köpnr = köpnr group by köpnr) then
insert into beställning(köpnr, summa, datum, kundID) values
(köpnr,(select produkt.pris from produkt where produkt.id = produktid),current_timestamp, kundid);

insert into skickar(köpnr, produktid) values
(köpnr, produktid);


else

-- Lägg till i beställningen samt öka köpnumret med 1.
insert into beställning(köpnr, summa, datum, kundID) values
(maxnumber +1 ,(select produkt.pris from produkt where produkt.id = produktid),current_timestamp, kundid);
insert into skickar(köpnr, produktid) values
(maxnumber +1, produktid);

-- update produkt
-- set lager = lager -1
-- where produkt.id = produktid;

end if;

end if;

commit;
set autocommit = 1;

end$$
delimiter ;

select * from beställning;
select * from produkt;
select * from skickar;


-- Köp nummer som redan finns!
call addtocart(1,7,2);

-- Köp nummer som är null
call addtocart(1,null,2);

-- Beställning samt produkt existerar redan

call addtocart(1,null,2);

-- Trigger

-- 1. 
call addtocart(1,1,1);
select * from produkt;
select * from slutilager;

-- -------------------------------------------------------------------------------------------------------------------------------------------------------- --
-- --------------------------------------------------------------------VG KRAVEN--------------------------------------------------------------------------- --
-- -------------------------------------------------------------------------------------------------------------------------------------------------------- --

-- Skapa en funktion som tar ett produktId som parameter och returnerar medelbetyget för den
-- produkten. Om du inte har sifferbetyg sedan innan, lägg till dessa, så att en siffra motsvarar ett av de
-- skriftliga betygsvärdena

DROP FUNCTION IF EXISTS hitta_betyg;

DELIMITER $$
CREATE FUNCTION hitta_betyg(produktid INT) 
RETURNS INTEGER LANGUAGE SQL
BEGIN

DECLARE medel INT; 

SET medel = (SELECT AVG(betygid) 
			FROM recension 
            WHERE recension.produktid = produktid
            and betygid > 0);

RETURN medel;
END$$
DELIMITER ;

select * from betyg;

select * from recension;

select hitta_betyg(2);

-- Skapa en vy som visar medelbetyget i siffror och i text för samtliga produkter (om en produkt inte
-- har fått något betyg så skall den ändå visas, med null eller tomt värde, i medelbetyg).

DROP VIEW IF EXISTS view_medelbetyg;

CREATE VIEW view_medelbetyg AS SELECT produkt.namn AS Namn, hitta_betyg(recension.produktid) AS Betyg, betyg.omdöme AS Omdöme
FROM produkt
LEFT JOIN recension
ON produkt.id = recension.produktid
LEFT JOIN betyg
ON hitta_betyg(recension.produktid) = betyg.betygnr         
GROUP BY produkt.namn;

SELECT * FROM view_medelbetyg;

-- Skapa en stored procedure ”Rate” som lägger till ett betyg och en kommentar 
-- på en specifik produkt för en specifik kund.

DROP PROCEDURE IF EXISTS rate;

delimiter $$
CREATE PROCEDURE Rate(betyg INT, kommentar VARCHAR(30), kundid INT, produkt INT)
BEGIN

INSERT INTO recension(kommentar, datum, betygid, produktid, kundid) 
VALUES (kommentar, current_time(), betyg, produkt, kundid);

INSERT INTO beskriver (recensionid, betygnr) 
VALUES((select max(id) from recension) , betyg);

END$$

delimiter ;

call rate(4, 'Bästaste!', 1, 1);

select * FROM recension;

select hitta_betyg(1);





