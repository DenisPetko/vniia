create table master
(
    id          uuid primary key,
    document_id bigint not null unique,
    date        date   not null,
    sum_details numeric(50, 2),
    comment     varchar(255)
);
create index idx_master_document_id on master (document_id);
comment on table master is 'таблица документов';
comment on column master.id is 'номер документа';
comment on column master.date is 'дата документа';
comment on column master.comment is 'комментарий';

create table detail
(
    id          uuid primary key,
    detail_name varchar(255)   not null,
    amount      numeric(50, 2) not null,
    master_id   bigint         NOT NULL,
    constraint fk_detail_document_id foreign key (master_id) references master (document_id)
);
comment on table detail is 'таблица спецификаций';
comment on column detail.id is 'уникальный идентификатор спецификации';
comment on column detail.detail_name is 'наименование спецификации';
comment on column detail.amount is 'сумма';
comment on column detail.master_id is 'идентификатор документа из таблицы master';

create function update_sum_details()
    returns trigger as
'
    begin
        update master
        set sum_details = (select coalesce(sum(amount), 0)
                           from detail
                           where master_id = new.master_id)
        where document_id = new.master_id;
        return new;
    end;
' language plpgsql;

create trigger sum_detail_update
    after insert or update or delete
    on detail
    for each row
execute function update_sum_details();

create table log_error
(
    id      uuid         not null,
    message varchar(255) not null,
    date    timestamp default now(),
    constraint pk_log_error primary key (id)
);
comment on table log_error is 'таблица логов ошибок';
comment on column log_error.id is 'уникальный идентификатор записи лога';
comment on column log_error.message is 'сообщение об ошибке';
comment on column log_error.date is 'дата и время записи';