// package edu.duke.ece651.team4.server;

// public class LoginChecker extends SecurityManager{
//     AccountManager accountManager;
//     public LoginChecker(AccountManager accountManager){
//         this.accountManager = accountManager;
//     }
//     public Boolean checkPassword(Login login) {
//         String currentUser = login.getUser();
//         if(!checkUserExist(currentUser)){
//             return false;
//         }
//         String password = login.getPassword();
//         String hashedpwd = hashPassword(password);
//         if(accountManager.getAccount(currentUser).getPassword().equals(hashedpwd)){
//             return true;
//         }
//         return false;
//     }

//     public Boolean checkUserExist(String netid) {
//         if (accountManager.getAccount(netid) != null){
//             return true;
//         }
//         return false;
//     }
// }
