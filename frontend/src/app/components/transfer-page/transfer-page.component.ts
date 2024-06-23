import { Component } from '@angular/core';
import { TransferService } from '../../services/transfer.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transfer-page',
  templateUrl: './transfer-page.component.html',
  styleUrls: ['./transfer-page.component.css']
})
export class TransferPageComponent {

  constructor(private transferService: TransferService, private router: Router) {}

  onSubmit(receiver_iban: string, amount: string): void {
    console.log("Hasta aquí llega");
    this.transferService.make_transfer(receiver_iban, amount).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/profile']);
      },
      error => {console.error('No se ha podido realizar la transferencia:', error);}
    );
  }
}
