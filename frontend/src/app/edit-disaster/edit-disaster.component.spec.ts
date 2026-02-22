import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditDisasterComponent } from './edit-disaster.component';

describe('EditDisasterComponent', () => {
  let component: EditDisasterComponent;
  let fixture: ComponentFixture<EditDisasterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditDisasterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditDisasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
