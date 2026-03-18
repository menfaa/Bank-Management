CREATE TABLE zahlung (                           -- Erstellt eine neue Tabelle namens 'zahlung'
    id UUID PRIMARY KEY,                         -- Spalte 'id' als Primärschlüssel (UUID, eindeutig)
    betrag NUMERIC NOT NULL,                     -- Spalte 'betrag' (Betrag der Zahlung), darf nicht NULL sein
    datum DATE NOT NULL,                         -- Spalte 'datum' (Datum der Zahlung), darf nicht NULL sein
    verwendungszweck VARCHAR(255),               -- Spalte 'verwendungszweck' (optional, max. 255 Zeichen)
    zahlungsart VARCHAR(31),                     -- Spalte 'zahlungsart' (z.B. SEPA, KARTE, BAR), für Vererbung/Diskriminator

    -- weitere Felder für Subklassen
    auftraggeber_iban VARCHAR(34) NULL,          -- IBAN des Auftraggebers (optional, max. 34 Zeichen)
    empfaenger_iban VARCHAR(34) NULL,            -- IBAN des Empfängers (optional, max. 34 Zeichen)
    empfaenger_name VARCHAR(255) NULL            -- Name des Empfängers (optional, max. 255 Zeichen)
);