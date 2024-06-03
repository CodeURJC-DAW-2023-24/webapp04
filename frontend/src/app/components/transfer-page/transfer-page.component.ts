import { Component } from '@angular/core';
import { TransferService } from '../../services/transfer.service';

@Component({
  selector: 'app-transfer-page',
  templateUrl: './transfer-page.component.html',
  styleUrl: './transfer-page.component.css'
})
export class TransferPageComponent {

  constructor(private transferService: TransferService) {}

  onSubmit(receiver_iban: string, amount: string): void {
    console.log("Hasta aquí llega");
    this.transferService.make_transfer(receiver_iban, amount);
  }
}
