import { TestBed } from '@angular/core/testing';

import { TransfersManagerService } from './trasnfer-manager.service';

describe('TransfersManagerService', () => {
  let service: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransfersManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
