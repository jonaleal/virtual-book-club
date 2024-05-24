-- Datos de prueba para la entidad User
INSERT INTO users (username, picture_url, email, password, role) VALUES
('admin', null, null, '$2a$10$arxeQ85iHOrXoCPO5WiJYeKxJEiFBb/1xwFbK5QVMKJ5egtuhHLfC', 'ADMIN'),
('usuario1', 'https://ejemplo.com/imagen1.jpg', 'usuario1@example.com', '$2a$10$NMYhhLAIjZRYOshZo27WfuVvky1OUtz.ueEUvuLnFvuXl68kxXWbu', 'USER'),
('usuario2', 'https://ejemplo.com/imagen2.jpg', 'usuario2@example.com', '$2a$10$2OphBMTB8i0IRrwr1KV53ucDjuFln0EcUs3Qi2jDGquVYb4bZp4Oa', 'USER'),
('usuario3', 'https://ejemplo.com/imagen3.jpg', 'usuario3@example.com', '$2a$10$kTwA4Bt61pwkLjQGNTfgou9Qq9Ar2/qg2yMNlGE6afatpQeWdhnMe', 'USER');

-- Datos de prueba para la entidad BookClub
INSERT INTO book_clubs (name, description, tags, meet_link, image_link, user_id) VALUES
('Club de Lectura "Viajeros del Tiempo"', 'Un club de lectura dedicado a discutir novelas de ciencia ficción y viajes en el tiempo. Únete a nosotros para explorar mundos nuevos y descubrir las posibilidades del universo.', 'ciencia ficción, viajes en el tiempo, novelas', 'https://meet.example.com/timetravelers', 'https://image.example.com/1', 1),
('Club de Lectura "Clásicos Literarios"', 'Sumérgete en la rica tradición literaria del mundo con nuestra selección de clásicos literarios. Desde Shakespeare hasta Tolstói, exploraremos obras atemporales y debatiremos sobre su relevancia en la sociedad actual.', 'clásicos, literatura, Shakespeare, Tolstói', 'https://meet.example.com/classics', 'https://image.example.com/2',  2),
('Club de Lectura "Historias de Misterio"', '¿Te encanta resolver acertijos y descifrar enigmas? Únete a nuestro club de lectura de misterio donde discutimos las mejores novelas de crimen, suspenso y detectives. Prepárate para sumergirte en la intriga y descubrir quién lo hizo.', 'misterio, crimen, detectives', 'https://meet.example.com/mystery', 'https://image.example.com/3', 3);

-- Datos de prueba para la entidad UserBookClub
INSERT INTO users_book_clubs (user_id, book_club_id) VALUES
(2, 1),
(3, 1),
(4, 2),
(2, 3);

-- Datos de prueba para la entidad Discussion
INSERT INTO discussions (title, description, created_at, user_id, book_club_id) VALUES
('Discusión: "El fin de la Eternidad"', 'Un debate sobre las implicaciones filosóficas y científicas de la novela "El fin de la Eternidad" de Isaac Asimov. ¿Cómo afectaría la manipulación del tiempo a la sociedad humana? Únete a nosotros para una conversación fascinante.', '2024-03-15', 1, 1),
('Debate: "Anna Karénina"', 'Un análisis profundo de la obra maestra de León Tolstói, "Anna Karénina". Discutiremos temas como el amor, la moralidad y la tragedia en la Rusia del siglo XIX. Todos son bienvenidos a compartir sus pensamientos y opiniones.', '2024-03-15', 2, 2),
('Charla: "El Asesinato en el Expreso de Oriente"', 'Exploraremos los misterios y giros de la famosa novela de Agatha Christie, "El Asesinato en el Expreso de Oriente". ¿Quién podría ser el asesino? Únete a nuestra discusión y haz tus propias conjeturas.', '2024-03-15', 3, 3);

-- Datos de prueba para la entidad Comment
INSERT INTO comments (comment, created_at, user_id, discussion_id) VALUES
('Estoy ansioso por discutir las teorías de viajes en el tiempo en "El fin de la Eternidad". ¡Espero que podamos descubrir cómo cambiaría el curso de la historia humana!', '2024-03-16', 1, 1),
('He estado reflexionando sobre la complejidad de los personajes en "Anna Karénina". La dualidad entre el deber y el deseo es verdaderamente intrigante.', '2024-03-16', 2, 2),
('¡No puedo esperar para resolver el misterio en "El Asesinato en el Expreso de Oriente"! ¿Alguien más tiene alguna teoría sobre quién podría ser el culpable?', '2024-03-16', 3, 3);
