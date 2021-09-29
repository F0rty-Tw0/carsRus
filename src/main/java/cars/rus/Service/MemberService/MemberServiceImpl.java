package cars.rus.Service.MemberService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cars.rus.DTO.MemberDTO.MemberDTO;
import cars.rus.DTO.MemberDTO.MemberInput;
import cars.rus.Entities.Member;
import cars.rus.Repositories.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
  private MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public List<MemberDTO> findAllMembers(boolean extended) {
    Iterable<Member> members = memberRepository.findAll();
    return MemberDTO.getMemberDTOs(members, extended);
  };

  public MemberDTO getMemberByEmail(String email, boolean extended) {
    Member member = memberRepository.getMemberByEmail(email);
    return MemberDTO.getMemberDTO(member, extended);
  }

  public List<MemberDTO> getMembersByApproved(boolean isApproved, boolean extended) {
    Iterable<Member> members = memberRepository.getMembersByApproved(isApproved);
    return MemberDTO.getMemberDTOs(members, extended);
  }

  public MemberDTO updateOrAddMember(MemberInput memberInput, Long id) {
    Optional<Member> foundMember = memberRepository.findById(id);
    Member newMember;
    if (foundMember.isPresent()) {
      newMember = memberRepository.save(MemberInput.getMemberFromInput(memberInput));
    } else {
      foundMember.get().setFirstName(memberInput.getFirstName());
      foundMember.get().setLastName(memberInput.getLastName());
      foundMember.get().setStreet(memberInput.getStreet());
      foundMember.get().setCity(memberInput.getCity());
      foundMember.get().setZip(memberInput.getZip());
      foundMember.get().setEmail(memberInput.getEmail());
      foundMember.get().setDateEdited(memberInput.getDateEdited());
      newMember = memberRepository.save(foundMember.get());
    }
    return new MemberDTO(newMember);
  }

  public MemberDTO findMemberById(Long id, boolean extended) {
    Optional<Member> foundMember = memberRepository.findById(id);
    return MemberDTO.getMemberDTO(foundMember.get(), extended);
  }

  public void deleteMemberById(Long id) {
    Optional<Member> foundMember = memberRepository.findById(id);
    if (!foundMember.isPresent()) {
      return;
    }
    memberRepository.deleteMemberById(id);
  }

  public MemberDTO addMember(MemberInput memberInput) {
    Member newMember = memberRepository.save(MemberInput.getMemberFromInput(memberInput));
    return new MemberDTO(newMember);
  }

}
