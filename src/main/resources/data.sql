-- some test users

INSERT INTO roles (id, role)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO players (id, email, first_name, last_name,  password, score)
VALUES
    (1, 'admin@example.com', 'Admin', 'Adminov', 'defaultPass', 400),
    (2, 'userOne@example.com', 'Player', 'NumberOne', 'defaultPass', 900),
    (3, 'userTwo@example.com', 'Player', 'NumberTwo', 'defaultPass', 150),
    (4, 'userThree@example.com', 'Player', 'NumberThree', 'defaultPass', 550);

INSERT INTO players_roles (player_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2);

INSERT INTO dictionary (id, word, definition)
VALUES (1, 'баница', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (2, 'слушалки', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (3, 'панталон', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio'),
       (4, 'ландшафт', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia, molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium optio');

