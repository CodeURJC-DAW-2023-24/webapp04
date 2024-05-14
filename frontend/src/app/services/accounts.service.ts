import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AccountsService {

  function loadMoreAccounts(startIndex: number, chunkSize: number): void {
      fetch(`/account_load?startIndex=${startIndex}&chunkSize=${chunkSize}`)
          .then((response: Response) => response.json())
          .then((data: Account[]) => {
              const accountsContainer: HTMLElement | null = document.querySelector('.accounts');
              if (accountsContainer) {
                  data.forEach((account: Account) => {
                      const accountHtml: string = `
                          <div class="account">
                              <dl class="account-details">
                                  <div>
                                      <dt>${account.nip}</dt>
                                      <dd>NIP</dd>
                                  </div>
                                  <div>
                                      <dt>${account.iban}</dt>
                                      <dd>IBAN</dd>
                                  </div>
                                  <div>
                                      <dt>${account.name}</dt>
                                      <dd>Name</dd>
                                  </div>
                                  <div>
                                      <dt>${account.surname}</dt>
                                      <dd>Surname</dd>
                                  </div>
                                  <div>
                                      <img src="data:image/jpeg;base64,${account.imageBase64}" style="width: 50px; height: 50px;"/>
                                  </div>
                              </dl>
                          </div>`;
                      if (accountsContainer) {
                          accountsContainer.insertAdjacentHTML('beforeend', accountHtml);
                      }
                  });
              }
          });
  }
}

export interface Account {
  nip: string;
  iban: string;
  name: string;
  surname: string;
  imageBase64: string;
}
