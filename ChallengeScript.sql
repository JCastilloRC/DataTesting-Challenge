USE PERSONAS; 
#-----------------1st---------------------------------------
CREATE TABLE Institution (
	idInstitution	INT,
	insName	VARCHAR(45) NOT NULL,
	phoneNumber		VARCHAR(45) NOT NULL,
	email		VARCHAR(45) NOT NULL,
	address		VARCHAR(45) NOT NULL,
	CONSTRAINT	PK_Institution_id  PRIMARY KEY (idInstitution)
);

INSERT INTO Institution (idInstitution, insName, phoneNumber, email, address) VALUES (
1, "Universidad Distrital", 3238340, "atencion@udistrital.edu.co", "Calle 13 # 31-75");
INSERT INTO Institution (idInstitution, insName, phoneNumber, email, address) VALUES (
2, "Universidad Nacional", 3165000, "mediosdigitales@unal.edu.co", "Carrera 45 # 26-85");
INSERT INTO Institution (idInstitution, insName, phoneNumber, email, address) VALUES (
3, "Universidad Javeriana", 3208320, "direstudiantes@javeriana.edu.co", "Cra 7 # 40-62");

ALTER TABLE Employee ADD idInstitution INT NOT NULL; 

UPDATE Employee
SET idInstitution = 1
WHERE idEmployee = 64;

UPDATE Employee
SET idInstitution = 3
WHERE idEmployee = 74;

UPDATE Employee
SET idInstitution = 2
WHERE idEmployee = 83;

UPDATE Employee
SET idInstitution = 1
WHERE idEmployee = 92;

UPDATE Employee
SET idInstitution = 3
WHERE idEmployee = 95;

ALTER TABLE Employee ADD CONSTRAINT FK_idInstitution  FOREIGN KEY (idInstitution)  
					REFERENCES Institution  (idInstitution)
                    ON DELETE CASCADE     
                    ON UPDATE CASCADE;	
								
SELECT * FROM Institution;
SELECT * FROM Employee;

#-----------------2nd---------------------------------------
SELECT e.idEmployee, e.firstName, e.lastName, i.insName
FROM Employee e
INNER JOIN Institution i ON e.idInstitution = i.idInstitution;

#-----------------3rd---------------------------------------
SELECT *
FROM Children
WHERE idEmployee IN
    (SELECT idEmployee
     FROM Employee
     WHERE idCompany = 5);
 