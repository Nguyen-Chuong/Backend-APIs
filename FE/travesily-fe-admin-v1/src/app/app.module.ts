import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { HotelApproveComponent } from './components/hotel-approve/hotel-approve.component';
import { HistoryComponent } from './components/history/history.component';
import { BookingListComponent } from './components/booking-list/booking-list.component';
import { TaskbarComponent } from './components/taskbar/taskbar.component';
import { HotelDetailComponent } from './components/hotel-detail/hotel-detail.component';
import { RoomDetailComponent } from './components/room-detail/room-detail.component';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HeaderComponent } from './components/header/header.component';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatMenuModule } from '@angular/material/menu';
import { AdminProfileComponent } from './components/admin-profile/admin-profile.component';
import { AuthInterceptor } from './_helper/auth.interceptor';
import { SimpleNotificationsModule } from 'angular2-notifications';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { NewAdminComponent } from './components/new-admin/new-admin.component';
import { MatPaginatorModule } from '@angular/material/paginator';
import { UserComponent } from './components/user/user.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';
import { NgxNavigationWithDataComponent } from "ngx-navigation-with-data";
import { UserBookingComponent } from './components/user-booking/user-booking.component';
import { BookingDetailComponent } from './components/booking-detail/booking-detail.component';
import { UserFeedbackComponent } from './components/user-feedback/user-feedback.component';
import {MatTabsModule} from '@angular/material/tabs';
import { HotelListComponent } from './components/hotel-list/hotel-list.component';
import { MatCheckboxModule } from '@angular/material/checkbox';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HotelApproveComponent,
    HistoryComponent,
    BookingListComponent,
    TaskbarComponent,
    HotelDetailComponent,
    RoomDetailComponent,
    HeaderComponent,
    AdminProfileComponent,
    ChangePasswordComponent,
    NewAdminComponent,
    UserComponent,
    UserDetailComponent,
    UserBookingComponent,
    BookingDetailComponent,
    UserFeedbackComponent,
    HotelListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FlexLayoutModule,
    MatToolbarModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatIconModule,
    MatTableModule,
    NgbModule,
    MatMenuModule,
    MatPaginatorModule,
    MatTabsModule,
    MatCheckboxModule,
    SimpleNotificationsModule.forRoot()
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    NgxNavigationWithDataComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
