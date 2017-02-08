/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ReportingRemoteDataService } from './reporting-remote-data.service';

describe('ReportingRemoteDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReportingRemoteDataService]
    });
  });

  it('should ...', inject([ReportingRemoteDataService], (service: ReportingRemoteDataService) => {
    expect(service).toBeTruthy();
  }));
});
