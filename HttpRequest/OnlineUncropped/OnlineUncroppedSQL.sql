
-- "c:\Program Files\MariaDB 10.1\bin\mysql.exe" -u root -D mysql -p
-- Enter password: 123456
--
-- source C:/Users/yahel/Documents/AI_Entertainment_Applications_MQP/HttpRequest/OnlineUncropped/OnlineUncroppedSQL.sql;
-- load data infile 'C:/Users/yahel/Documents/AI_Entertainment_Applications_MQP/HttpRequest/OnlineUncropped/ServiceTagsData.txt' into table ServiceTags fields terminated by ',' lines terminated by '\n';
drop table ServiceTags;
drop table ServiceResponseTimes;
drop table FileProperties;

create table FileProperties(
	indexNum int primary key,
	categoryPath varchar(100),
	xPixels int,
	yPixels int,
	totalPixels int
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
	tag varchar(150),
	score float,
	tagCorrect char(1),
	constraint Primary Key (indexNum, service, tag, score),
	constraint Foreign Key (indexNum) references FileProperties (indexNum)
);

load data infile 'C:/FilePropertiesData.txt' 
into table FileProperties
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


-- total number of pictures
select count(indexNum)
from  fileproperties;

-- number of pictures classified correctly
select count(ST.indexNum)
from
	(select distinct indexNum
	from serviceTags
	where tagCorrect = 'Y') ST;

-- number of pictures classified correctly by each service
select service, count(indexNum) 
from serviceTags 
where tagCorrect = 'Y' 
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

-- output clarifai correct analyzations vs scores
select indexNum, service, tag, score, tagCorrect 
from serviceTags st
where service = 'clarifai' and tagCorrect = 'Y'
into outfile '\\clarifaicorrecttagvsscore.csv'
FIELDS TERMINATED BY ','
lines terminated by '\n';