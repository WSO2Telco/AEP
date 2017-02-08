/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { DashboardRemoteDataService } from './dashboard-remote-data.service';

describe('DashboardRemoteDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DashboardRemoteDataService]
    });
  });

  it('should ...', inject([DashboardRemoteDataService], (service: DashboardRemoteDataService) => {
    expect(service).toBeTruthy();
  }));
});
