import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { HeaderComponent } from './componants/header/header.component';
import { FooterComponent } from './componants/footer/footer.component';
import { SignUpComponent } from './componants/sign-up/sign-up.component';
import { ContactComponent } from './componants/contact/contact.component';
import { UserhomeComponent } from './componants/userhome/userhome.component';
import { LeftBarComponent } from './componants/userhome/left-bar/left-bar.component';
import { RightBarComponent } from './componants/userhome/right-bar/right-bar.component';
import { PublishComponent } from './componants/userhome/publish/publish.component';
import { FriendsComponent } from './componants/userhome/friends/friends.component';
import { CoolImagesComponent } from './componants/userhome/cool-images/cool-images.component';
import { MainPageComponent } from './componants/userhome/main-page/main-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    SignUpComponent,
    ContactComponent,
    UserhomeComponent,
    LeftBarComponent,
    RightBarComponent,
    PublishComponent,
    FriendsComponent,
    CoolImagesComponent,
    MainPageComponent,
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
