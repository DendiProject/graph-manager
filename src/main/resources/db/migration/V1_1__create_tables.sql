create table Catalog 
(catalog_id varchar(255) not null, 
    description varchar(255), 
    name varchar(255),
    CONSTRAINT catalog_pkey PRIMARY KEY (catalog_id)
)
WITH (
    OIDS = FALSE
);

create table Edges (
edge_id varchar(255) not null, 
end_node_id varchar(255), 
start_node_id varchar(255), 
CONSTRAINT edges_pkey PRIMARY KEY (edge_id)    
)
WITH (
    OIDS = FALSE
);

create table Node (
    node_id varchar(255) not null, 
    description varchar(255), name varchar(255), 
    node_number integer, person_number integer, 
    picture_id varchar(255), version_id varchar(255), 
    CONSTRAINT node_pkey PRIMARY KEY (node_id)
)
WITH (
    OIDS = FALSE
);

create table NodeResources (
    input_or_output varchar(255), 
    number_of_resource varchar(255), 
    resource_id varchar(255) not null, 
    node_id varchar(255), 
    version_id varchar(255), 
    CONSTRAINT noderesources_pkey PRIMARY KEY (resource_id)
)
WITH (
    OIDS = FALSE
);

create table Receipe (
    receipe_id varchar(255) not null, 
    description varchar(255), 
    is_completed boolean, 
    is_deleted boolean, 
    is_public boolean, 
    name varchar(255), 
    picture_id varchar(255), 
    catalog_id varchar(255), 
    CONSTRAINT receipe_pkey PRIMARY KEY (receipe_id)
)
WITH (
    OIDS = FALSE
);

create table ReceipeVersion (
version_id varchar(255) not null, 
is_main_version boolean, 
number_of_people integer, 
user_id varchar(255), 
receipe_id varchar(255), 
CONSTRAINT version_pkey PRIMARY KEY (version_id)
)
WITH (
    OIDS = FALSE
);
create table Resources (
resource_id varchar(255) not null, 
ingredient_or_resource varchar(255), 
measuring varchar(255), 
name varchar(255), 
user_id varchar(255), 
CONSTRAINT resources_pkey PRIMARY KEY (resource_id)
)
WITH (
    OIDS = FALSE
);
create table Tags (
tag_id varchar(255) not null,
 name varchar(255), 
receipe_id varchar(255), 
CONSTRAINT tag_pkey PRIMARY KEY (tag_id)
)
WITH (
    OIDS = FALSE
);
create table UserStep (
step_id varchar(255) not null, 
is_completed boolean, 
user_id varchar(255),
node_id varchar(255), 
version_id varchar(255),
CONSTRAINT step_pkey PRIMARY KEY (step_id)
)
WITH (
    OIDS = FALSE
);

alter table Node add constraint FK151ij77y0a4tt2wer8adxay4h foreign key (version_id) references ReceipeVersion;
alter table NodeResources add constraint FKq2ke9oxnmh7685mbskwhrptff foreign key (resource_id) references Resources;
alter table NodeResources add constraint FKsmj1pap8p9dcckopqiwcbbcvy foreign key (node_id) references Node;
alter table NodeResources add constraint FKfsetiemf0h8b40k0hachxq7fl foreign key (version_id) references ReceipeVersion;
alter table Receipe add constraint FKdas5suiytegofttyi961i2bcw foreign key (catalog_id) references Catalog;
alter table ReceipeVersion add constraint FKeokmd6bcfe5fgvvx4g8cbc50n foreign key (receipe_id) references Receipe;
alter table Tags add constraint FKjeu3btdqlodjgsgw09f2jmnp8 foreign key (receipe_id) references Receipe;
alter table Edges add constraint FKbhrkb857l3ovgv0134skfi9nf foreign key (end_node_id) references Node;
alter table Edges add constraint FKjq0a1a85w4edxbrdgmo392ey7 foreign key (start_node_id) references Node;
alter table UserStep add constraint FK4yj9gl2grk3pasi0j4a21s2q3 foreign key (version_id) references ReceipeVersion;
alter table UserStep add constraint FKt69hxm5viytprnfpfovcxwr7c foreign key (version_id) references Node;