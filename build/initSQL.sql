create table User (
	userId BIGINT(16),
	name VARCHAR(12) NOT NULL,
	email VARCHAR(60) NOT NULL,
	password VARCHAR(20) NOT NULL,
	role VARCHAR(10) NOT NULL,
	CONSTRAINT UK_1 UNIQUE(name),
	CONSTRAINT UK_2 UNIQUE(email),
	CONSTRAINT PK_1 PRIMARY KEY(userid)
);
create table Topic (
	topicId BIGINT(16),
	userId BIGINT(16),
	title VARCHAR(280) NOT NULL,
	content TEXT NOT NULL,
	INDEX(content(20)),
	replyCount INT(9) NOT NULL DEFAULT 0,
	topictime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT PK_2 PRIMARY KEY(topicId),
	CONSTRAINT FK_1 FOREIGN KEY(userId) REFERENCES User(userId) ON DELETE CASCADE
);
create table Reply (
	replyId BIGINT(16),
	userId BIGINT(16),
	topicId BIGINT(16),
	content TEXT NOT NULL,
	INDEX(content(20)),
	inreplyto BIGINT(16),
	rateCount INT(4),
	score DOUBLE(5,2),
	replytime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT PK_3 PRIMARY KEY(replyid),
	CONSTRAINT FK_2 FOREIGN KEY(userId) REFERENCES User(userId) ON DELETE CASCADE,
	CONSTRAINT FK_3 FOREIGN KEY(topicId) REFERENCES Topic(topicId) ON DELETE CASCADE
)