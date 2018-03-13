/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { ServeurTestModule } from '../../../test.module';
import { ObstacleComponent } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.component';
import { ObstacleService } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.service';
import { Obstacle } from '../../../../../../main/webapp/app/entities/obstacle/obstacle.model';

describe('Component Tests', () => {

    describe('Obstacle Management Component', () => {
        let comp: ObstacleComponent;
        let fixture: ComponentFixture<ObstacleComponent>;
        let service: ObstacleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ServeurTestModule],
                declarations: [ObstacleComponent],
                providers: [
                    ObstacleService
                ]
            })
            .overrideTemplate(ObstacleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ObstacleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ObstacleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Obstacle('123')],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.obstacles[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
