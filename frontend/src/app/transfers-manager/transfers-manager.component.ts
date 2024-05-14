import { Component } from '@angular/core';
import { loadMoreTransfers, Transfer } from './services/transfers-manager.service';

@Component({
  selector: 'app-transfers-manager',
  templateUrl: './transfers-manager.component.html',
  styleUrl: './transfers-manager.component.css'
})
export class TransfersManagerComponent {
 transfers: Transfer[] = [];

    constructor() {}

    ngOnInit(): void {
        this.loadInitialTransfers();
    }

    loadInitialTransfers(): void {
        // Cargar las primeras transferencias al iniciar el componente
        loadMoreTransfers(0, 10).then(data => {
            this.transfers = data;
        });
    }

    onLoadMoreTransfers(): void {
        const startIndex: number = this.transfers.length;
        const chunkSize: number = 10;

        loadMoreTransfers(startIndex, chunkSize).then(data => {
            // Concatenar las nuevas transferencias con las existentes
            this.transfers = this.transfers.concat(data);
        });
    }

      logout(): void {
        const body = {};
        this.http.post('/api/logout', body, { observe: 'response' }).subscribe({
          next: (response) => {
            console.log('Logout exitoso');
            this.router.navigate(['new/login']);
          },
          error: (error) => {
            console.error('Error en logout:', error);
          }
        })
      }
}
