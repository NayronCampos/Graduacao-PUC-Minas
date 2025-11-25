
-- Popular capturador
INSERT INTO capturador (nome, data_nascimento, sexo) VALUES
('Carlos Henrique Duarte', '1984-02-18', 'M'),
('Juliana Pereira da Costa', '1991-09-05', 'F'),
('Rogério Martins Alves', '1987-11-23', 'M');

-- Popular animal
INSERT INTO animal (fk_capturador, nome, raca, idade, tipo, local_captura, data_captura) VALUES
(1, 'Bolt', 'Vira-Lata', 14, 'cachorro', 'Centro', '2024-04-12'),
(1, 'Nina', 'Siamês', 10, 'gato', 'Savassi', '2024-05-27'),
(2, 'Thor', 'Pastor Alemão', 26, 'cachorro', 'Barreiro', '2024-06-03'),
(3, 'Mia', 'Persa', 8, 'gato', 'Venda Nova', '2024-07-14'),
(2, 'Lola', 'Vira-Lata', 12, 'cachorro', 'Pampulha', '2024-08-02');

-- Popular veterinário
INSERT INTO veterinario (cpf, nome, area_atuacao) VALUES
('01749235681', 'Dr. Pedro Augusto Silva', 'Clínica Geral'),
('42891567320', 'Dra. Ana Carolina Moura', 'Felinos'),
('89234715609', 'Dr. Mateus Figueiredo Rocha', 'Cirurgia Veterinária');

-- Popular instituição
INSERT INTO instituicao (nome, cidade) VALUES
('Pet Lares Centro', 'Belo Horizonte'),
('Pet Lares Barreiro', 'Belo Horizonte'),
('Pet Lares Contagem', 'Contagem');

-- Popular voluntário
INSERT INTO voluntario (matricula, nome, telefone, sexo, data_nascimento) VALUES
(101, 'Mariana Lopes Prado', '31998546612', 'F', '1999-03-21'),
(102, 'Lucas Carvalho Ribeiro', '31987442219', 'M', '2000-10-13'),
(103, 'Fernanda Oliveira Lima', '31997735544', 'F', '1998-07-29');

-- Popular receptor
INSERT INTO receptor (cpf, nome, endereco, data_nascimento) VALUES
('31785592040', 'Paulo Roberto Santos', 'Rua das Palmeiras, 120 - BH', '1994-05-11'),
('50231986472', 'Renata Aparecida Martins', 'Rua das Magnólias, 455 - Contagem', '1986-09-28'),
('71245839012', 'João Pedro Rocha', 'Rua dos Limoeiros, 782 - BH', '2000-01-19');

-- Popular adoção
INSERT INTO adotar (fk_animal, fk_receptor, fk_veterinario_responsavel, data_adocao) VALUES
(1, '31785592040', '01749235681', '2024-06-02'),
(4, '50231986472', '42891567320', '2024-09-05'),
(5, '71245839012', '89234715609', '2024-10-15');

-- Popular atendimento
INSERT INTO atendido (fk_animal, fk_veterinario, data_atendimento) VALUES
(1, '01749235681', '2024-04-15'),
(2, '42891567320', '2024-05-30'),
(3, '89234715609', '2024-06-10'),
(4, '42891567320', '2024-07-20'),
(5, '01749235681', '2024-08-07');

-- Popular ajuda
INSERT INTO ajuda (fk_instituicao, fk_voluntario, horas) VALUES
(1, 101, 42),
(1, 102, 18),
(2, 101, 16),
(2, 103, 29),
(3, 102, 12);