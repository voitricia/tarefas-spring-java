INSERT INTO categoria (nome) VALUES ('Trabalho');
INSERT INTO categoria (nome) VALUES ('Estudos');
INSERT INTO categoria (nome) VALUES ('Pessoal');
INSERT INTO categoria (nome) VALUES ('Saúde');
INSERT INTO categoria (nome) VALUES ('Urgente');

INSERT INTO tarefa (titulo, descricao, responsavel, prioridade, status, data_criacao, data_limite) VALUES 
('Finalizar relatório', 'Relatório sobre o projeto X', 'João', 'ALTA', 'PENDENTE', '2023-10-01', '2023-10-05'),
('Estudar para prova', 'Revisar os capítulos 3 e 4', 'Maria', 'MEDIA', 'PENDENTE', '2023-10-02', '2023-10-10'),
('Limpar a casa', 'Faxina geral na casa', 'Pedro', 'BAIXA', 'PENDENTE', '2023-10-02', '2023-10-04'),
('Consulta médica', 'Consulta com o Dr. Silva', 'Ana', 'BAIXA', 'PENDENTE', '2023-10-03', '2023-10-15'),
('Comprar mantimentos', 'Lista de compras para a semana', 'Carlos', 'ALTA', 'PENDENTE', '2023-10-04', '2023-10-07'),
('Organizar festa de aniversário', 'Planejar a festa de aniversário da Ana', 'Fernanda', 'MEDIA', 'PENDENTE', '2023-10-05', '2023-10-20');

INSERT INTO users (username, password, role) VALUES ('admin', 'admin', 'ADMIN');