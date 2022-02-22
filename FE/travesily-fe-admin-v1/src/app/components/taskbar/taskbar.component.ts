import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServiceService } from 'src/app/auth-service.service';


@Component({
  selector: 'app-taskbar',
  templateUrl: './taskbar.component.html',
  styleUrls: ['./taskbar.component.scss']
})
export class TaskbarComponent implements OnInit {
  opened = true;
  constructor(private router: Router,
    public authService: AuthServiceService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
   }

  ngOnInit(): void {
  }

  openApprovingHotels(): void{
    this.router.navigateByUrl('/hotel-approve');
  }

  openHistory(): void{
    this.router.navigate(['/history']);
  }

  openBookingList(): void{
    this.router.navigate(['/booking']);
  }

  logout(): void{
    this.authService.logout();
    this.authService.isLoggedOut();
    this.router.navigate(['/login']);
  }

  toggleSidebar(){
    this.opened = !this.opened
  }
}
