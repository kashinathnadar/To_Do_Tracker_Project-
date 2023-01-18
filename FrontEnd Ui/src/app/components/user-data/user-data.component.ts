import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { UrlsService } from 'src/app/services/urls.service';

@Component({
  selector: 'app-user-data',
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.css']
})
export class UserDataComponent implements OnInit {

  constructor(private url:UrlsService, @Inject(MAT_DIALOG_DATA) private dialodData: any){ }
data:any;
  ngOnInit(): void {
    //  this.getUser();
    if (this.dialodData) {
      this.data = this.dialodData;
    }}

    // getUser()
    // {
    //   this.url.getUserDetails().subscribe(x=>
    //     {
    //       this.data=x;
    //       console.log("user Details");
    //     })
    // }

}
