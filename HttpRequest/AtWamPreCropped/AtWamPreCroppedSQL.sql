
-- "c:\Program Files\MariaDB 10.1\bin\mysql.exe" -u root -D mysql -p
-- Enter password: 123456
--
-- source C:/Users/yahel/Documents/AI_Entertainment_Applications_MQP/HttpRequest/OnlineUncropped/OnlineUncroppedSQL.sql;
-- load data infile 'C:/Users/yahel/Documents/AI_Entertainment_Applications_MQP/HttpRequest/OnlineUncropped/ServiceTagsData.txt' into table ServiceTags fields terminated by ',' lines terminated by '\n';

drop table FoundTags;
drop table ServiceTags;
drop table ServiceResponseTimes;
drop table AcceptedTags;
drop table AcceptedAndSimilarTags;
drop table FileProperties;

-- FileProperties(indexNum, categoryPath, xPixels, yPixels, totalPixels)
-- AcceptedAndSimilarTags(acceptedTag, similarTag)
-- AcceptedTags(indexNum, acceptedTag)
-- ServiceResponseTimes(indexNum, service, responseTime)
-- ServiceTags(indexNum, service, actualTag, score)
-- FoundTags(indexNum, service, actualTag, acceptedTag, similarTag)

create table FileProperties(
	indexNum int primary key,
	categoryPath varchar(100),
	xPixels int,
	yPixels int,
	totalPixels int
);

create table AcceptedAndSimilarTags(
	acceptedTag varchar(25),
	similarTag varchar(25),
	constraint PRIMARY KEY (acceptedTag, similarTag)
);

create table AcceptedTags (
	indexNum int,
	acceptedTag varchar(25),
	constraint Primary Key (indexNum, acceptedTag),
	constraint FOREIGN KEY (indexNum) references FileProperties (indexNum),
	constraint Foreign KEY (acceptedTag) references AcceptedAndSimilarTags (acceptedTag)
);

create table ServiceResponseTimes (
	indexNum int,
	service varchar(20),
	responseTime int,
	constraint Primary Key (indexNum, service),
	constraint Foreign Key (indexNum) references FileProperties (indexNum)
);

create table ServiceTags(
	indexNum int,
	service varchar(20),
	actualTag varchar(150),
	score float,
	constraint Primary Key (indexNum, service, actualTag, score),
	constraint Foreign Key (indexNum, service) references ServiceResponseTimes (indexNum, service)
);

create table FoundTags (
	indexNum int,
	service varchar(20),
	actualTag varchar(150),
	acceptedTag varchar(25),
	similarTag varchar(25),
	constraint primary key (indexNum, service, actualTag, acceptedTag, similarTag),
	constraint foreign key (indexNum, acceptedTag) references AcceptedTags (indexNum, acceptedTag),
	constraint foreign key (indexNum, service, actualTag) references ServiceTags(indexNum, service, actualTag),
	constraint foreign key (acceptedTag, similarTag) references AcceptedAndSimilarTags (acceptedTag, similarTag)
);

load data infile 'C:/FilePropertiesData.txt' 
into table FileProperties
fields terminated by ',' 
lines terminated by '\n';


load data infile 'C:/AcceptedAndSimilarData.txt' 
into table AcceptedAndSimilarTags
fields terminated by ',' 
lines terminated by '\n';

load data infile 'C:/AcceptedTagsData.txt' 
into table AcceptedTags
fields terminated by ',' 
lines terminated by '\n';

load data infile 'C:/ServiceResponseTimesData.txt' 
into table ServiceResponseTimes
fields terminated by ',' 
lines terminated by '\n';

load data infile 'C:/ServiceTagsData.txt' 
into table ServiceTags 
fields terminated by ',' 
lines terminated by '\n';

load data infile 'C:/FoundTagsData.txt' 
into table FoundTags
fields terminated by ',' 
lines terminated by '\n';


-- total number of pictures
select count(indexNum)
from  fileproperties;

-- number of pictures classified correctly
select count(distinct indexnum)
from foundtags;

-- number of pictures classified correctly by each service
select service, count(distinct indexnum)
from foundtags
group by service;

-- the average response from each service
select service, avg(responsetime)
from serviceresponsetimes
group by service;

-- export services, total amount of pixels, and response time
select srt.service, fp.totalPixels, srt.responseTime
from fileproperties fp, serviceresponsetimes srt
where fp.indexNum = srt.indexNum
into outfile '\\pixelvsresponsetime.csv'
FIELDS TERMINATED BY ','
lines terminated by '\n';

-- number of pictures analyzed by cloudsight
select count(*)
from serviceresponsetimes srt
where srt.service = 'cloudsight';

-- number of pictures analyzed by cloudsight with response time below 25 seconds
select count(*)
from serviceresponsetimes srt
where srt.service = 'cloudsight' and srt.responseTime < 25000;

-- number of pictures analyzed by cloudsight with response time below 15 seconds
select count(*)
from serviceresponsetimes srt
where srt.service = 'cloudsight' and srt.responseTime < 15000;

-- get response times of cloudsight requests
select responseTime
from serviceresponsetimes srt
where srt.service = 'cloudsight'
into outfile '\\cloudsightresponsetimes.csv'
FIELDS TERMINATED BY ','
lines terminated by '\n';

select acceptedTag, count(*)
from foundtags
group by acceptedTag
order by count(*);

select acceptedTag, count(*)
from acceptedTags
group by acceptedTag
order by count(*);

select service, actualtag, count(*)
from servicetags
group by service, actualtag
order by service, count(*);

select acceptedTag, count(*)
from acceptedTags
group by acceptedTag
order by count(*);