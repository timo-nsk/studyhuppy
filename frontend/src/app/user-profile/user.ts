export interface User {
  userId : string;
  mail : string;
  username : string;password : string;
  notificationSubscription : boolean;
  semester : number;
  profilbildPath : string;
}

export interface UserGroupSessionDto {
  userId: string;
  username: string;
}
