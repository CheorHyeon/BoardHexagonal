## 📑 프로젝트 개요

**BoardHexagonal** 은 Spring Boot를 이용해  
- 전통적인 **레이어드 아키텍처**  
- **헥사고날(Ports & Adapters) 아키텍처**  

두 가지 방식으로 동일한 간단 CRUD 기능(게시판 게시물 생성/조회/수정/삭제)을 구현하고, 코드 구조를 비교 학습할 수 있도록 만든 연습용 예제입니다.

---

## 🔍 아키텍처 비교

| 구분               | 레이어드 아키텍처                                    | 헥사고날 아키텍처                                      |
|------------------|-------------------------------------------------|----------------------------------------------------|
| **핵심 위치**        | `controller` → `service` → `repository` → `entity` | `adapters/in/web` → `application` → `domain/ports` → `adapters/out/persistence` |
| **의존성 방향**      | 위→아래                                            | 모두 **도메인 코어**(POJO+Port)로 향하도록 설계                           |
| **도메인 & DTO 분리** | 엔티티와 API DTO가 섞여 있을 수 있음                  | DTO↔도메인 매핑은 **입력 어댑터**에서만 담당                            |
| **확장성 & 테스트**   | 계층 늘어날수록 테스트·교체 비용 증가                   | Adapter만 교체하면 DB(MyBatis/JPA) 등 교체 용이                   |

---

## 📂 디렉터리 구조

### Layered Architecture

![image](https://github.com/user-attachments/assets/f5c02456-ebc2-401e-97b7-492396e055ce)

```bash
📦 com.example.layeredarchitecture.board
├── controller
│   └── BoardController.java    # REST 컨트롤러
├── service
│   └── BoardService.java       # 서비스 클래스 (비즈니스 로직)
├── repository
│   └── BoardRepository.java    # JPA 리포지토리 인터페이스
└── entity
    └── Board.java              # 엔티티
└── dto
    └── AllBoardResponse.java     # 게시글 전체 응답 Dto
    └── BoardCreateRequestDto.java     # 게시글 작성 요청 Dto
    └── BoardDetail.java     # 게시글 상세 응답 Dto
    └── UpdateBoardRequestDto.java     # 게시글 수정 요청 Dto
```

### Layered Architecture 구현 코드
[feat : layered Architecture CRUD 완성](https://github.com/CheorHyeon/BoardHexagonal/pull/1)

### Hexagonal Architecture

![image](https://github.com/user-attachments/assets/d4f75c6b-a88e-436e-9c53-55cedd982765)


```bash
com.example.hexagonal.board
├── domain                             # Pure Domain (POJO + Port 인터페이스)
│   ├── Board.java                     # 순수 도메인 모델
│   ├── BoardService.java              # Use Case Port
│   └── BoardRepository.java           # Persistence Port
├── application                        # Use Case 구현체
│   └── BoardServiceImpl.java
└── adapters
    ├── in
    │   └── web
    │       ├── controller
    │       │   └── BoardController.java
    │       ├── dto
    │       │   ├── BoardCreateRequestDto.java
    │       │   ├── BoardDetailResponseDto.java
    │       │   └── AllBoardResponseDto.java
    │       └── mapper  # DTO <-> 순수 도메인 모델
    │           ├── BoardRequestMapper.java
    │           └── BoardResponseMapper.java
    └── out
        └── persistence
            ├── entity
            │   └── BoardEntity.java # JPA Entity
            ├── SpringDataBoardEntityRepository.java  # JPA Repository
            └── BoardRepositoryAdapter.java
```

### Hexagonal Architecture 구현 코드
[Hexagonal 도입](https://github.com/CheorHyeon/BoardHexagonal/pull/2)

## 구조 전환 가이드

### 1. 도메인 모델 POJO 추출 -> Port 인터페이스 정의

#### POJO 엔티티 분리
- 기존 Entity에서 JPA 어노테이션(`@Entity`, `@Table` 등) 제거
- `domain/Board.java` 로 옮겨 "순수 필드 + 비즈니스 로직(검증 메서드 등)"만 남김

#### Use Case Port (입력 포트) 정의
- `domain/BoardService.java` 인터페이스 생성
- CRUD 시그니처 선언
  - 아래 사용된 Board는 Jpa Entity가 아닌 domain Entity
```java
public interface BoardService {
	List<Board> getAllBoards();
	Board getBoard(Long id);
	Board createBoard(Board board);
	Board updateBoard(Long id, Board board);
	void deleteBoard(Long id);
}
```

#### Persistence Port (출력 포트) 정의
- `domain/BoardRepository.java` 인터페이스 생성
- 데이터 접근에 필요한 시그니처 선언
  - 아래 사용된 Board는 Jpa Entity가 아닌 domain Entity
```java
public interface BoardRepository {
	List<Board> findAll();
	Optional<Board> findById(Long id);
	Board save(Board board);
	void deleteById(Long id);
}
```

### 2. Service 로직 -> `application` 계층 이동

#### 패키지 생성 & Use Case 구현체 파일 생성
```bash
com.example.hexagonal.board
└── application
    └── BoardServiceImpl.java
```

#### 기존 서비스 로직 옮기기
- service/BoardService.java 클래스 내용을 그대로 복사
- implements BoardService 선언
- @Service + @Transactional 어노테이션 추가
- 생성자에 BoardRepository 포트 인터페이스 주입

### 3. DB 접근(JPA) -> `adapters/out/persistence` 어댑터로 분리

#### 엔티티 재정의
```bash
com.example.hexagonal.board
└── adapters
    └── out
        └── persistence
            └── entity
                └── BoardEntity.java   # JPA 어노테이션 보유
```

#### Spring Data JPA 레포지토리 생성
```java
public interface SpringDataBoardEntityRepository
    extends JpaRepository<BoardEntity, Long> { }
```

#### Persistence Port 구현체 작성
- JPA Entity에서 domain Entity를 사용하는데, 이거는 문제가 없는것이 domain으로 의존방향 가도 되기 때문!
```java
@Repository
@RequiredArgsConstructor
public class BoardRepositoryAdapter implements BoardRepository {
    private final SpringDataBoardEntityRepository jpa;
    // findAll(), findById(), save(), deleteById() 를
    // BoardEntity ↔ Board.toDomain()/fromDomain() 으로 매핑
}
```

### 컨트롤러 -> Dto 매핑 -> `adapters/in/web/mapper` 로 분리

#### DTO 패키지 구성
```bash
adapters/in/web
├── dto
│   ├── BoardCreateRequestDto.java
│   ├── BoardDetailResponseDto.java
│   └── AllBoardResponseDto.java
```

#### Mapper 패키지 구성
```bash
adapters/in/web
└── mapper
    ├── BoardRequestMapper.java    // DTO → Board 도메인
    └── BoardResponseMapper.java   // Board 도메인 → DTO
```

#### Controller 수정
- 의존성 주입 변경
```java
private final BoardService       service;
private final BoardRequestMapper rqMapper;
private final BoardResponseMapper rsMapper;
```
