CREATE TABLE inhaber (
    inhaber_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE girokonto (
    iban VARCHAR(255) PRIMARY KEY,
    inhaber_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (inhaber_id) REFERENCES inhaber (inhaber_id)
);

CREATE TABLE kontoauszug (
    id BIGSERIAL PRIMARY KEY,
    girokonto_iban VARCHAR(255) NOT NULL,
    datum DATE NOT NULL,
    betrag DOUBLE PRECISION NOT NULL,
    verwendungszweck VARCHAR(255) NOT NULL,
    transaktionsart VARCHAR(31) NOT NULL, -- Diskriminatorspalte für Vererbung

    -- SEPA-spezifische Felder (nullable, weil nicht jede Zeile eine SEPA-Überweisung ist)
    empfaenger_iban VARCHAR(255),
    empfaenger_name VARCHAR(255),

    -- Karten-spezifische Felder (nullable, weil nicht jede Zeile eine Kartenzahlung ist)
    kartennummer VARCHAR(255) ,
    haendler VARCHAR(255) ,

    FOREIGN KEY (girokonto_iban) REFERENCES girokonto (iban)
);