import { TestBed } from '@angular/core/testing';

import { TransfersManagerService } from './transfers-manager.service';

describe('TransfersManagerService', () => {
  let service: TransfersManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransfersManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
