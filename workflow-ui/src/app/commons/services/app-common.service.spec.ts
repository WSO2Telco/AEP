/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { AppCommonService } from './app-common.service';

describe('AppCommonService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AppCommonService]
    });
  });

  it('should ...', inject([AppCommonService], (service: AppCommonService) => {
    expect(service).toBeTruthy();
  }));
});
