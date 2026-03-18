CREATE TABLE inhaber (                       -- Erstellt eine neue Tabelle namens 'inhaber'
    inhaber_id VARCHAR(255) PRIMARY KEY,     -- Spalte 'inhaber_id' als Primärschlüssel (eindeutige ID, max. 255 Zeichen)
    name VARCHAR(255) NOT NULL               -- Spalte 'name' (Name des Inhabers), darf nicht NULL sein, max. 255 Zeichen
);