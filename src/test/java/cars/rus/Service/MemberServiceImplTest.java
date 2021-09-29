package cars.rus.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cars.rus.Configuration.JpaDataMock;
import cars.rus.DTO.MemberDTO.MemberDTO;
import cars.rus.Entities.Member;
import cars.rus.Repositories.CarRepository;
import cars.rus.Repositories.MemberRepository;
import cars.rus.Repositories.ReservationRepository;
import cars.rus.Service.MemberService.MemberServiceImpl;

@DataJpaTest
public class MemberServiceImplTest {
  MemberDTO memberDTO = new MemberDTO("Artiom", "Tofan", "30 Commercial Road", "New York", "1526", "arty@gmail.com");
  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private CarRepository carRepository;

  MemberServiceImpl memberServiceImpl;

  @BeforeEach
  public void initService() {
    memberServiceImpl = new MemberServiceImpl(memberRepository);
  }

  @BeforeEach
  public void setupMembers() {
    JpaDataMock.setupData(carRepository, memberRepository, reservationRepository);
  }

  @Test
  void testGetMemberByEmail() {
    Long foundMemberId = memberServiceImpl.getMemberByEmail("art@gmail.com", true).getId();
    assertTrue(foundMemberId > 0);
  }

  @Test
  void testGetMembersByApproved() {
    int foundMembers = memberServiceImpl.getMembersByApproved(false, true).size();
    assertEquals(4, foundMembers);
  }

  @Test
  void testUpdateOrAddMember() {
    Long lastMemberId = memberRepository.findTopByOrderByIdDesc().getId();
    String updatedFirstName = memberServiceImpl.updateOrAddMember(memberDTO, lastMemberId).getFirstName();
    assertEquals("Artiom", updatedFirstName);
  }

  @Test
  void testFindAllMembers() {
    int foundMembers = memberServiceImpl.findAllMembers(true).size();
    assertEquals(5, foundMembers);
  }

  @Test
  void testAddMember() {
    String newMemberId = memberServiceImpl.addMember(memberDTO).getFirstName();
    assertEquals(newMemberId, memberDTO.getFirstName());
  }

  @Test
  void testDeleteMemberById() {
    memberServiceImpl.deleteMemberById(1l);
    Optional<Member> foundMember = memberRepository.findById(1l);
    assertTrue(!foundMember.isPresent());
  }

  @Test
  void testFindMemberById() {
    Long lastMemberId = memberRepository.findTopByOrderByIdDesc().getId();
    System.out.println(lastMemberId);
    String lastMemberFirstName = memberRepository.findTopByOrderByIdDesc().getFirstName();
    String foundMemberFirstName = memberServiceImpl.findMemberById(lastMemberId, true).getFirstName();
    assertEquals(lastMemberFirstName, foundMemberFirstName);
  }
}
