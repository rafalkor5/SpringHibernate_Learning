alter table tasks_groups add column project_id int null;
alter table tasks_groups add foreign key (project_id) references projects(id);