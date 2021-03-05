SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));
SET GLOBAL log_bin_trust_function_creators = 1;

use `database_webshop`;

drop user if exists 'salah'@'localhost';

CREATE USER 'salah'@'localhost' IDENTIFIED BY 'salah';
GRANT ALL PRIVILEGES ON * . * TO 'salah'@'localhost';


drop trigger if exists lager_trigger;	  
-- Trigger som kolla när lagret är slut och lägger produkten i tabellen: slutilager.
delimiter $$ 
create trigger lager_trigger
before update on produkt
for each row
begin

if new.lager = 0 then

insert into slutilager(datum, produktid) values
(current_timestamp, new.id);

end if;

end$$

delimiter ;

drop function if exists findmaxnumber;
-- Funktion som hittar senaste köpet.
delimiter $$
CREATE FUNCTION findMaxNumber() RETURNS INTEGER LANGUAGE SQL
begin

declare maxnumber int default (select max(köpnr) from beställning);

return maxnumber;

end $$
delimiter ;

drop function if exists findAverageNumber;
-- Funktion som hittar genomsnitts nummer. 
DELIMITER $$
CREATE FUNCTION findAverageNumber (produkt int) RETURNS INTEGER LANGUAGE SQL
BEGIN

DECLARE averageFromProduct int;

SET averageFromProduct = (select avg(betygid) from recension where produktid = produkt and betygid > 0);

RETURN averageFromProduct;

END$$
DELIMITER ;

DROP FUNCTION IF EXISTS findInt;

DELIMITER $$
CREATE FUNCTION findInt(köpt int) RETURNS INTEGER LANGUAGE SQL
begin

declare maxnumber int default 0;

set maxnumber = köpt;

return maxnumber;

end $$

DELIMITER ;