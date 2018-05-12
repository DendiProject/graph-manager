create table Catalog 
(catalog_id varchar(255) not null, 
    description_id varchar(255), 
    name varchar(255),
    CONSTRAINT catalog_pkey PRIMARY KEY (catalog_id)
);
create table TopOfReceipes
(top_id varchar(255) not null,
 user_id varchar(255) not null,
 receipe_id varchar(255) not null,
frequency_of_use int,
CONSTRAINT top_pkey PRIMARY KEY (top_id)
);
create table Edges (
edge_id varchar(255) not null, 
end_node_id varchar(255), 
start_node_id varchar(255), 
CONSTRAINT edges_pkey PRIMARY KEY (edge_id)    
);

create table Node (
    node_id varchar(255) not null, 
    description_id varchar(255), 
    label varchar(255),  
    picture_id varchar(255), 
    version_id varchar(255), 
    CONSTRAINT node_pkey PRIMARY KEY (node_id)
);

create table NodeResources (
    node_resource_id varchar(255),
    input_output varchar(255), 
    number_of_resource float8, 
    resource_id varchar(255), 
    node_id varchar(255), 
    version_id varchar(255), 
    previous_node_id varchar(255), 
    CONSTRAINT noderesources_pkey PRIMARY KEY (node_resource_id)
);

create table Receipe (
    receipe_id varchar(255) not null, 
    description_id varchar(255), 
    is_completed boolean, 
    is_deleted boolean, 
    is_public boolean, 
    name varchar(255), 
    picture_id varchar(255), 
    catalog_id varchar(255), 
    CONSTRAINT receipe_pkey PRIMARY KEY (receipe_id)
);

create table ReceipeVersion (
version_id varchar(255) not null, 
is_main_version boolean, 
is_paralell boolean,
user_id varchar(255), 
receipe_id varchar(255), 
CONSTRAINT version_pkey PRIMARY KEY (version_id)
);

create table Resources (
resource_id varchar(255) not null, 
ingredient_or_resource varchar(255), 
measuring varchar(255), 
name varchar(255), 
user_id varchar(255), 
node_id varchar(255), 
picture_id varchar(255),
CONSTRAINT resources_pkey PRIMARY KEY (resource_id)
);

create table Tags (
tag_id varchar(255) not null,
 name varchar(255), 
CONSTRAINT tag_pkey PRIMARY KEY (tag_id)
);


create table Sessions ( 
session_id varchar(255),
CONSTRAINT session_pkey PRIMARY KEY (session_id)
);
create table UserStep (
step_id varchar(255) not null, 
session_id varchar(255),
is_completed boolean, 
is_started boolean,
node_id varchar(255), 
version_id varchar(255),
user_id varchar(255) ,
CONSTRAINT step_pkey PRIMARY KEY (step_id)
);

create table InvitedUsers ( 
invited_id varchar(255),
user_id varchar(255),
session_id varchar(255),
CONSTRAINT users_pkey PRIMARY KEY (invited_id)
);


alter table Node add constraint FK151ij77y0a4tt2wer8adxay4h foreign key (version_id) references ReceipeVersion;
alter table NodeResources add constraint FKq2ke9oxnmh7685mbskwhrptff foreign key (resource_id) references Resources;
alter table NodeResources add constraint FKsmj1pap8p9dcckopqiwcbbcvy foreign key (node_id) references Node;
alter table NodeResources add constraint FKfsetiemf0h8b40k0hachxq7fl foreign key (version_id) references ReceipeVersion;
alter table NodeResources add constraint FKsmj1pap8p9dcckopqiwcbbcva foreign key (previous_node_id) references Node;
alter table Receipe add constraint FKdas5suiytegofttyi961i2bcw foreign key (catalog_id) references Catalog;
alter table ReceipeVersion add constraint FKeokmd6bcfe5fgvvx4g8cbc50n foreign key (receipe_id) references Receipe;
alter table Edges add constraint FKbhrkb857l3ovgv0134skfi9nf foreign key (end_node_id) references Node;
alter table Edges add constraint FKjq0a1a85w4edxbrdgmo392ey7 foreign key (start_node_id) references Node;
alter table UserStep add constraint FK4yj9gl2grk3pasi0j4a21s2q3 foreign key (version_id) references ReceipeVersion;
alter table UserStep add constraint FKt69hxm5viytprnfpfovcxwr7c foreign key (node_id) references Node;
alter table Resources add constraint nodeIdFK foreign key (node_id) references Node;
alter table TopOfReceipes add constraint receipeIdFK foreign key (receipe_id) references Receipe;

CREATE TABLE Receipe_tagList 
    (Receipe_receipe_id varchar(255) not null, 
    tagList_tag_id varchar(255) not null);
alter table Receipe_tagList add constraint FK9g52vxo4gnsilfckxug56tgt9 foreign key (tagList_tag_id) references Tags;
alter table Receipe_tagList add constraint FKgfgd00q37nwo8s5opbgjjiv8e foreign key (Receipe_receipe_id) references Receipe;

alter table UserStep add constraint sessionsIdFK foreign key (session_id) references Sessions;
alter table InvitedUsers add constraint sessionIdFK foreign key (session_id) references Sessions;