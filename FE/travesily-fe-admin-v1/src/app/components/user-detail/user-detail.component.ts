import { Component, OnInit } from '@angular/core';
import { NgxNavigationWithDataComponent } from 'ngx-navigation-with-data';
import { first } from 'rxjs';
import { Account } from 'src/app/_models/account';
import { Booking } from 'src/app/_models/booking';
import { BookingService } from 'src/app/_services/booking.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.scss']
})
export class UserDetailComponent implements OnInit {
  account: Account
  bookings: Booking[]

  constructor(public navCtrl: NgxNavigationWithDataComponent, private bookingService: BookingService) {
    const userId = this.navCtrl.get('id')
    console.log(userId)
    this.bookingService.getBookingByStatus(userId, 1).pipe(first()).subscribe(
      rs => {
        this.bookings = rs['data']
      }
    )
  }

  ngOnInit(): void {
  }

}