DECLARE num NUMBER;
BEGIN
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_category';
IF
num = 0 THEN
	EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_category" (
	"id" NVARCHAR2(64) NOT NULL ,
	"name" NVARCHAR2(50) NOT NULL ,
	"parent_id" NUMBER(20) ,
	"del_flag" NCHAR(1) ,
	"create_by" NVARCHAR2(64) ,
	"create_time" DATE ,
	"update_by" NVARCHAR2(64) ,
	"update_time" DATE ,
	"remark" NVARCHAR2(500) ,
	"ancestors" NVARCHAR2(50)
	) TABLESPACE "USERS"
	LOGGING
	NOCOMPRESS
	PCTFREE 10
	INITRANS 1
	STORAGE (
	BUFFER_POOL DEFAULT
	)
	PARALLEL 1
	NOCACHE
	DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_dept';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_dept" (
		"dept_id" NVARCHAR2(64) NOT NULL ,
		"dept_name" NVARCHAR2(255) ,
		"parent_id" NVARCHAR2(64) ,
		"del_flag" NCHAR(1) NOT NULL
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_dept_mapping';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_dept_mapping" (
		"dept_id" NVARCHAR2(64) NOT NULL ,
		"user_id" NVARCHAR2(64) NOT NULL ,
		"is_leader" NCHAR(1) NOT NULL
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_form';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_form" (
		"id" NVARCHAR2(64) NOT NULL ,
		"name" NVARCHAR2(50) NOT NULL ,
		"table_code" NVARCHAR2(50) NOT NULL ,
		"category_id" NUMBER(20) ,
		"category_name" NVARCHAR2(255) ,
		"form_type" NCHAR(1) ,
		"component_path" NCLOB ,
		"form_data" NCLOB ,
		"del_flag" NCHAR(1) ,
		"create_by" NVARCHAR2(64) ,
		"create_time" DATE ,
		"update_by" NVARCHAR2(64) ,
		"update_time" DATE ,
		"remark" NVARCHAR2(500)
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_form_model';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_form_model" (
		"id" NVARCHAR2(64) NOT NULL ,
		"form_id" NVARCHAR2(64) NOT NULL ,
		"model_id" NVARCHAR2(64) NOT NULL
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_instance_busines';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_instance_busines" (
		"id" NVARCHAR2(64) NOT NULL ,
		"instance_id" NVARCHAR2(36) ,
		"business_key" NVARCHAR2(32) ,
		"table_code" NVARCHAR2(32) ,
		"status" NVARCHAR2(255) ,
		"result" NVARCHAR2(255) ,
		"applay_time" DATE ,
		"create_user_id" NVARCHAR2(64) ,
		"create_time" DATE ,
		"deployment_id" NVARCHAR2(100) ,
		"proc_def_id" NVARCHAR2(100) ,
		"title" NVARCHAR2(255) ,
		"process_name" NVARCHAR2(255)
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;
num := 0;
SELECT
    count( * ) INTO num
FROM
    user_tables
WHERE
        table_name = 'cube_flowable_node';
IF
num = 0 THEN
		EXECUTE IMMEDIATE 'CREATE TABLE "cube_flowable_node" (
		"id" NVARCHAR2(64) NOT NULL ,
		"node_id" NVARCHAR2(255) NOT NULL ,
		"user_type" NVARCHAR2(255) ,
		"relate_id" NVARCHAR2(255) ,
		"proc_def_id" NVARCHAR2(255) ,
		"del_flag" NCHAR(1) ,
		"create_by" NVARCHAR2(255) ,
		"create_time" DATE ,
		"model_id" NVARCHAR2(255)
		) TABLESPACE "USERS"
    LOGGING
    NOCOMPRESS
    PCTFREE 10
    INITRANS 1
    STORAGE (
		INITIAL 65536
		NEXT 1048576
		MINEXTENTS 1
		MAXEXTENTS 2147483645
		BUFFER_POOL DEFAULT
    )
    PARALLEL 1
    NOCACHE
    DISABLE ROW MOVEMENT';

END IF;

END;