/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { LoginRemoteDataService } from './login_remote-data.service';

describe('LoginRemoteDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [LoginRemoteDataService]
    });
  });

  it('should ...', inject([LoginRemoteDataService], (service: LoginRemoteDataService) => {
    expect(service).toBeTruthy();
  }));
});
