import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScalaFormGroupDemoComponent } from './scala-form-group-demo.component';

describe('ScalaFormGroupDemoComponent', () => {
  let component: ScalaFormGroupDemoComponent;
  let fixture: ComponentFixture<ScalaFormGroupDemoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScalaFormGroupDemoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScalaFormGroupDemoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
