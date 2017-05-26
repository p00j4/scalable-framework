class NewFile{
/**

*/
  
  // new line 1
  // new line 2
  // new line 3
  
  <<<<<<< qa
    admin = UsersForDashboardAccess.getUsersForDashboardAccessObjectByFilter(db_name,kwargs={'role':'Admin','active':True},sort=('last_updated', pymongo.ASCENDING))
=======
    admins = UsersForDashboardAccess.getUsersForDashboardAccessObjectByFilter(db_name,kwargs={'role':'Admin','active':True},sort=('last_updated', pymongo.ASCENDING))
    admin = admins[0]
>>>>>>> dev
}
