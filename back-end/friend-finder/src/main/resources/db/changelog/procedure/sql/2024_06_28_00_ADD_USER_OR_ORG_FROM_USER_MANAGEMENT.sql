DROP PROCEDURE IF EXISTS ADD_USER_OR_ORG_FROM_USER_MANAGEMENT;
CREATE PROCEDURE ADD_USER_OR_ORG_FROM_USER_MANAGEMENT
    @USER_ID INT,
    @SCOPE VARCHAR(50)
AS
BEGIN
    INSERT INTO User_System (active
                            ,created_time
                            ,modified_time
                            ,scope
                            ,user_id)
    VALUES (1, GETDATE(), GETDATE(), @SCOPE, @USER_ID)
END;