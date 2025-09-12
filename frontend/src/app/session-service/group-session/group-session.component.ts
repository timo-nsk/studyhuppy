import {ChangeDetectorRef, Component} from '@angular/core';
import {User, UserGroupSessionDto} from '../../user-profile/user';
import {NgForOf, NgIf} from '@angular/common';

@Component({
  selector: 'app-group-session',
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './group-session.component.html',
  standalone: true,
  styleUrl: './group-session.component.scss'
})
export class GroupSessionComponent {

  userFriends : UserGroupSessionDto[] = [
      {
        userId: "1111-2222-3333-4444",
        username: 'peter999'
      },
      {
        userId: "1111-2222-3333-4445",
        username: 'schnulli'
      },
      {
        userId: "1111-2222-3333-4446",
        username: 'susanne11'
      },
      {
        userId: "1111-2222-3333-4447",
        username: 'darkschpromp'
      },
      {
        userId: "1111-2222-3333-4448",
        username: 'coolilolo'
    }
    ]

    selectedUsersForSession : UserGroupSessionDto[] = []
    isFullQueue = false


  putUser(e: Event) {
    const selectElement = e.target as HTMLSelectElement;
    const selectedId = selectElement.value;

    const user = this.userFriends.find(u => u.userId.toString() === selectedId);
    if (user && !this.selectedUsersForSession.includes(user)) {
      this.selectedUsersForSession = [...this.selectedUsersForSession, user];
    }
    console.log("put new user: ", this.selectedUsersForSession)

    this.checkFullQueue()
  }

  checkFullQueue() {
    if (this.selectedUsersForSession.length == 5) {
      this.isFullQueue = true
    } else {
      this.isFullQueue = false
    }
  }

  popUser(userId: string) {
    this.selectedUsersForSession = this.selectedUsersForSession.filter(
      u => u.userId !== userId
    );

    this.checkFullQueue()

    console.log("pop user: ", this.selectedUsersForSession)
  }
}
