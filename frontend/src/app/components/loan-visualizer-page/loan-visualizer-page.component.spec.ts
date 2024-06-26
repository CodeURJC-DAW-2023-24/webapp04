import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanVisualizerPageComponent } from './loan-visualizer-page.component';

describe('LoanVisualizerPageComponent', () => {
  let component: LoanVisualizerPageComponent;
  let fixture: ComponentFixture<LoanVisualizerPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoanVisualizerPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoanVisualizerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
