-- Tabelle für Kontoinhaber
CREATE TABLE inhaber (
    inhaber_id VARCHAR(255) PRIMARY KEY, -- Eindeutige ID des Inhabers
    name VARCHAR(255) NOT NULL           -- Name des Inhabers
);

-- Tabelle für Girokonten
CREATE TABLE girokonto (
    iban VARCHAR(255) PRIMARY KEY,           -- IBAN als Primärschlüssel
    inhaber_id VARCHAR(255) NOT NULL,        -- Fremdschlüssel auf Inhaber
    FOREIGN KEY (inhaber_id) REFERENCES inhaber (inhaber_id) -- Verknüpfung mit Inhaber
);

-- Tabelle für Kontoauszüge (Transaktionen)
CREATE TABLE kontoauszug (
    id BIGSERIAL PRIMARY KEY,                -- Eindeutige ID (automatisch fortlaufend)
    girokonto_iban VARCHAR(255) NOT NULL,    -- Fremdschlüssel auf Girokonto
    datum DATE NOT NULL,                     -- Buchungsdatum
    betrag DOUBLE PRECISION NOT NULL,        -- Betrag der Transaktion
    verwendungszweck VARCHAR(255) NOT NULL,  -- Verwendungszweck
    transaktionsart VARCHAR(31) NOT NULL,    -- Diskriminatorspalte für Vererbung (z.B. "SEPA", "KARTE")

    -- SEPA-spezifische Felder (nullable, weil nicht jede Zeile eine SEPA-Überweisung ist)
    empfaenger_iban VARCHAR(255),            -- IBAN des Empfängers (nur bei SEPA)
    empfaenger_name VARCHAR(255),            -- Name des Empfängers (nur bei SEPA)

    -- Karten-spezifische Felder (nullable, weil nicht jede Zeile eine Kartenzahlung ist)
    kartennummer VARCHAR(255),               -- Kartennummer (nur bei Kartenzahlung)
    haendler VARCHAR(255),                   -- Händler (nur bei Kartenzahlung)

    FOREIGN KEY (girokonto_iban) REFERENCES girokonto (iban) -- Verknüpfung mit Girokonto
);