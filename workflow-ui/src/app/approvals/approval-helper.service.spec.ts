/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ApprovalHelperService } from './approval-helper.service';

describe('ApprovalHelperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ApprovalHelperService]
    });
  });

  it('should ...', inject([ApprovalHelperService], (service: ApprovalHelperService) => {
    expect(service).toBeTruthy();
  }));
});
