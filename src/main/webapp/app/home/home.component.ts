import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import {
  faEllipsisH,
  faAddressCard,
  faMapMarked,
  faBookOpen,
  faMap,
  faMonument,
  faMusic,
  faBookMedical,
  faMoneyBillWave,
  faQuidditch,
} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  faEllipsisH = faEllipsisH;
  faMoneyBillWave = faMoneyBillWave;
  faBookOpen = faBookOpen;
  faMonument = faMonument;
  faQuidditch = faQuidditch;
  faMap = faMap;
  faMusic = faMusic;
  faBookMedical = faBookMedical;
  account: Account | null = null;
  authSubscription?: Subscription;
  menuConfig = {
    isOpenByHover: false,
    isSymmetrical: false,
    isClockwiseMovement: true,
    subStep: 75,
    mainStep: 180,
    startedDeg: 260,
    animateName: 'come-in',
    animateTime: 300,
    mainRadius: 200,
    subRadius: 140,
  };
  faAddressCard = faAddressCard;
  faMapMarked = faMapMarked;
  constructor(private accountService: AccountService, private loginModalService: LoginModalService) {}

  ngOnInit(): void {
    this.authSubscription = this.accountService.getAuthenticationState().subscribe(account => (this.account = account));
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  login(): void {
    this.loginModalService.open();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }
}
