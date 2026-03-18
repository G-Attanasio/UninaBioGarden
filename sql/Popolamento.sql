TRUNCATE TABLE ANOMALIA,
ATTIVITAIMMINENTE,
NOTIFICADESTINATARIO,
NOTIFICA,
RACCOLTA,
IRRIGAZIONE,
SEMINACOLTURA,
SEMINA,
ATTIVITA,
PROGETTOCOLTURA,
COLTIVATOREPROGETTO,
COLTURA,
PROGETTOSTAGIONALE,
LOTTOCOLTIVABILE,
UTENTE RESTART IDENTITY CASCADE;

INSERT INTO
	COLTURA
VALUES
	(
		'0001',
		'Pomodoro',
		'Solanum Lycopersicum',
		'Solanaceae',
		75,
		'Umano',
		'PRIMAVERA_ESTATE'
	),
	(
		'0002',
		'Cetriolo',
		'Cucumis Sativus',
		'Cucurbitaceae',
		60,
		'Umano',
		'PRIMAVERA_ESTATE'
	),
	(
		'0003',
		'Mais',
		'Zea Mays',
		'Poaceae',
		100,
		'Umano',
		'PRIMAVERA_ESTATE'
	),
	(
		'0004',
		'Cipolla',
		'Allium Cepa',
		'Amaryllidaceae',
		120,
		'Umano',
		'INVERNO_PRIMAVERA'
	),
	(
		'0005',
		'Zucca',
		'Cucurbita',
		'Cucurbitaceae',
		150,
		'Umano',
		'PRIMAVERA_ESTATE'
	),
	(
		'0006',
		'Verza',
		'Brassica Oleracea',
		'Brassicaceae',
		100,
		'Umano',
		'AUTUNNO_INVERNO'
	),
	(
		'0007',
		'Soia',
		'Glycine max',
		'Fabaceae',
		130,
		'Industriale',
		'PRIMAVERA_ESTATE'
	),
	(
		'0008',
		'Avena',
		'Avena sativa',
		'Poaceae',
		80,
		'Foraggio',
		'AUTUNNO_INVERNO'
	),
	(
		'0009',
		'Camelina',
		'Camelina sativa',
		'Brassicaceae',
		90,
		'Biodiesel',
		'PRIMAVERA_ESTATE'
	);

INSERT INTO
	UTENTE (
		NOME,
		COGNOME,
		USERNAME,
		PASSWORD,
		EMAIL,
		DATANASCITA,
		RUOLO
	)
VALUES
	(
		'Mario',
		'Rossi',
		'marioR',
		'1234',
		'mario@unina.it',
		'1985-01-01',
		'PROPRIETARIO'
	),
	(
		'Luigi',
		'Verdi',
		'luigiV',
		'4567',
		'luigi@unina.it',
		'1990-05-10',
		'COLTIVATORE'
	),
	(
		'Laura',
		'Bianchi',
		'lauraB',
		'7890',
		'laura@unina.it',
		'1982-12-31',
		'PROPRIETARIO_COLTIVATORE'
	),
	(
		'Antonio',
		'Neri',
		'antonioN',
		'1234',
		'antonio@unina.it',
		'1995-07-16',
		'COLTIVATORE'
	);

INSERT INTO
	LOTTOCOLTIVABILE (
		TESSITURA,
		DIMENSIONI,
		PH,
		MORFOLOGIA,
		ALTITUDINE,
		LOCALITA,
		COMUNE,
		PROVINCIA,
		FK_IDPROPRIETARIO
	)
VALUES
	(
		'MEDIO_IMPASTO',
		10000,
		6.5,
		'PIANEGGIANTE',
		50,
		'Piana del Sole',
		'Napoli',
		'NA',
		1
	),
	(
		'ARGILLOSO',
		1200,
		7,
		'MONTUOSO',
		800,
		'Acquedotto carolino',
		'Caserta',
		'CE',
		1
	),
	(
		'SABBIOSO',
		45000,
		6.2,
		'COLLINARE',
		400,
		'Belvedere di San leucio',
		'Caserta',
		'CE',
		3
	);

INSERT INTO
	PROGETTOSTAGIONALE (
		NOMEPROGETTO,
		STAGIONEDIRIFERIMENTO,
		DURATA,
		DATAINIZIO,
		DATAFINE,
		STATOESECUZIONE,
		FK_CODLOTTO,
		FK_CREATORE
	)
VALUES
	(
		'Progetto Pomodoro',
		'PRIMAVERA_ESTATE',
		120,
		'2026-04-15',
		NULL,
		'PIANIFICATO',
		1,
		1
	),
	(
		'Progetto Mais',
		'PRIMAVERA_ESTATE',
		160,
		'2026-04-20',
		NULL,
		'PIANIFICATO',
		2,
		1
	),
	(
		'Progetto Verza',
		'AUTUNNO_INVERNO',
		130,
		'2025-10-10',
		'2026-02-17',
		'COMPLETATO',
		3,
		3
	);

INSERT INTO
	PROGETTOCOLTURA
VALUES
	(1, '0001'),
	(2, '0003'),
	(3, '0006');

INSERT INTO
	COLTIVATOREPROGETTO (FK_CODPROGETTO, FK_COLTIVATORE)
VALUES
	(1, 2),
	(1, 3),
	(1, 4),
	(2, 2),
	(2, 3),
	(2, 4),
	(3, 2),
	(3, 3),
	(3, 4);

INSERT INTO
	ATTIVITA (
		STATOESECUZIONE,
		DATAINIZIO,
		DATAFINE,
		FK_COLTIVATORE,
		FK_CODPROGETTO
	)
VALUES
	('COMPLETATO', '2025-10-12', '2025-10-14', 2, 3),
	('PIANIFICATO', '2026-04-21', '2026-04-25', 3, 1),
	('COMPLETATO', '2026-02-24', '2026-02-27', 4, 2);

INSERT INTO
	SEMINA (METODOSEMINA, FK_CODATTIVITA)
VALUES
	('POSTARELLE', 1),
	('FILE', 2),
	('SPAGLIO', 3);

INSERT INTO
	SEMINACOLTURA (QUANTITASEMI, FK_CODATTIVITA, FK_CODCOLTURA)
VALUES
	(15.0, 1, '0006'),
	(11.3, 2, '0001'),
	(250, 3, '0003');

INSERT INTO
	ATTIVITA (
		STATOESECUZIONE,
		DATAINIZIO,
		DATAFINE,
		FK_COLTIVATORE,
		FK_CODPROGETTO
	)
VALUES
	('COMPLETATO', '2025-11-01', '2026-01-30', 2, 3),
	('PIANIFICATO', '2026-04-26', '2026-08-10', 3, 1),
	('PIANIFICATO', '2026-04-28', '2026-09-10', 4, 2);

INSERT INTO
	IRRIGAZIONE (METODOIRRIGAZIONE, FK_CODATTIVITA)
VALUES
	('ASPERSIONE', 4),
	('SCORRIMENTO', 5),
	('LOCALIZZATA', 6);

INSERT INTO
	ATTIVITA (
		STATOESECUZIONE,
		DATAINIZIO,
		DATAFINE,
		FK_COLTIVATORE,
		FK_CODPROGETTO
	)
VALUES
	('COMPLETATO', '2026-02-10', '2026-02-15', 2, 3),
	('PIANIFICATO', '2026-08-10', '2026-08-14', 3, 1),
	('PIANIFICATO', '2026-09-20', '2026-09-24', 4, 2);

INSERT INTO
	RACCOLTA (
		METODORACCOLTA,
		QUANTITAPREVISTA,
		QUANTITAREALE,
		FK_CODATTIVITA,
		FK_CODCOLTURA
	)
VALUES
	('MECCANIZZATA', 500.0, 480.5, 7, '0006'),
	('MECCANICA', 300, null, 8, '0001'),
	('MANUALE', 500, null, 9, '0003');

	
