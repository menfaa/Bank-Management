CREATE TABLE zahlung (
    id UUID PRIMARY KEY,
    betrag NUMERIC NOT NULL,
    datum DATE NOT NULL,
    verwendungszweck VARCHAR(255),
    zahlungsart VARCHAR(31),
    -- weitere Felder für Subklassen
    auftraggeber_iban VARCHAR(34) NULL,
    empfaenger_iban VARCHAR(34) NULL,
    empfaenger_name VARCHAR(255) NULL
);