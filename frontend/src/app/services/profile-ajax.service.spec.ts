import { TestBed } from '@angular/core/testing';

import { ProfileAjaxService } from './profile-ajax.service';

describe('ProfileAjaxService', () => {
  let service: ProfileAjaxService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfileAjaxService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
