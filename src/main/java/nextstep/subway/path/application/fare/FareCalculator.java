package nextstep.subway.path.application.fare;

import nextstep.subway.auth.domain.LoginMember;
import nextstep.subway.line.domain.Section;
import nextstep.subway.path.application.fare.policy.DistanceOverFarePolicy;
import nextstep.subway.path.application.fare.policy.MaxLineOverFarePolicy;

import java.util.Collections;
import java.util.List;


public class FareCalculator {

    private final int distance;
    private final LoginMember loginMember;
    private final MaxLineOverFarePolicy maxLineOverFarePolicy;
    private final DistanceOverFarePolicy distanceOverFarePolicy;

    public FareCalculator(int distance) {
        this(distance, Collections.emptyList(), new LoginMember());
    }

    public FareCalculator(int distance, List<Section> sections) {
        this(distance, sections, new LoginMember());
    }

    public FareCalculator(int distance, List<Section> sections, LoginMember loginMember) {
        this.distance = distance;

        this.loginMember = loginMember;
        maxLineOverFarePolicy = new MaxLineOverFarePolicy(sections);
        distanceOverFarePolicy = DistanceOverFarePolicy.valueOf(distance);
    }

    public int calculateFare() {
        int fare = distanceOverFarePolicy.calculateFare(distance);
        fare = maxLineOverFarePolicy.calculateFare(fare);
        return loginMember.calculateFare(fare);
    }
}