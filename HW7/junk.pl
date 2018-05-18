
% Pedram Safaei
% CS 326
% HW7


%-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

%Question 1

%1a)  rules for a predicate isSet(S), which succeeds if the list S is a set.
% Input: isSet([1,2,5]). -> Yes    isSet([1,2,1,5]). -> No
isSet([]).
isSet([_,[]]).
isSet([H|T]):-not(member(H,T)),isSet(T).

%------------------------------------------------------------------------------------------------

%1b) rules for a predicate subset(A,S), which succeeds if the set A is a subset of the set S.
% Input: subset([2,5], [1,5,3,2]). -> Yes
subset([],_).
subset([A|R],S) :- member(A,S), subset(R,S), !.


%------------------------------------------------------------------------------------------------


%1c)union of set A and B is the set C (succeed)
% Input: union([2,5,4], [1,5,3,2], C). -> C = [4,1,5,3,2]
union([X|Y],Z,W) :- member(X,Z),  union(Y,Z,W),!.
union([X|Y],Z,[X|W]) :- \+ member(X,Z), union(Y,Z,W),!.
union([],Z,Z).


%------------------------------------------------------------------------------------------------


%1d) if the intersection of A and B is the set C (succeed)
% Input: intersection([2,5,4], [1,5,3,2], C). -> C = [2,5]
intersection([], _, []).
intersection([H|L1T], L2, L3) :- memberchk(H, L2), !, L3 = [H|L3T], intersection(L1T, L2, L3T),!.
intersection([_|L1T], L2, L3) :- intersection(L1T, L2, L3),!.


%-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

%Question 2
%tally(E, L, N). if N is the number of occurences of element  E in the list L (succeed)
% Input: tally(3, [1,2,3,1,2,3], N). -> N = 2

tally(_, [], 0) :- !. /* empty list, base case */

tally(X, [X|T], N) :- tally(X, T, N2), N is N2 + 1,!.

tally(X, [Y|T], N) :- X \= Y, tally(X, T, N),!.
%-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

%Question 3
%subst(X, Y, L, L1). if L1 is identical to list L (succeed) and all occurences of x and L are replaced with Y in L1
% Input: subst(1, 2, [1,2,3,1,2,3], L1). -> L1 = [2,2,3,2,2,3]
subst(_,_,[],[]).
subst(A, B, [A|T], [B|T2]) :- subst(A, B, T, T2),!.
subst(A, B, [H|T], [H|T2]) :- H \=A, subst(A, B, T, T2),!.

%-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

%Question 4
%insert(X, L, L1). Inserts X into the correct spot in ordered list L, L1 is the list with X inserted.
% Input: insert(5, [1,3,4,7], L1). -> L1 = [1,3,4,5,7]
insert(X, [], [X]).
insert(X, [H|T], [X,H|T]) :- X < H, !.
insert(X, [H|T0], [H|T]) :- insert(X, T0, T).

%-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

%Question 5 - Extra Credit
%flatten(A, B)
%	Input: flatten([1, [2, [3, 4]], 5], L). -> L = [1, 2, 3, 4, 5]

flatten([], []) :- !.
flatten([H|T], B) :- !, flatten(H, C), flatten(T, D), append(C, D, B).
flatten(A, [A]).
