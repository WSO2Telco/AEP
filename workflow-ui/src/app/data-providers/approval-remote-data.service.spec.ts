/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ApprovalRomoteDataService } from './approval-remote-data.service';

describe('ApprovalRomoteDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApprovalRomoteDataService]
    });
  });

  it('should ...', inject([ApprovalRomoteDataService], (service: ApprovalRomoteDataService) => {
    expect(service).toBeTruthy();
  }));
});
