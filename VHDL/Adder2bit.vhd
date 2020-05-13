----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    19:15:55 05/13/2020 
-- Design Name: 
-- Module Name:    Adder2bit - Behavioral 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity Adder2bit is
	Port ( 
		A : in  STD_LOGIC_VECTOR (1 downto 0);
		B : in  STD_LOGIC_VECTOR (1 downto 0);
		CarryIn : in  STD_LOGIC;
		S : out  STD_LOGIC_VECTOR (1 downto 0);
		CarryOut : out  STD_LOGIC
	);
end Adder2bit;

architecture Structural of Adder2bit is

begin
	component FullAdder is 
		Port (
			A : in  STD_LOGIC;
			B : in  STD_LOGIC;
			CarryIn : in  STD_LOGIC;
			S : out  STD_LOGIC;
			CarryOut : out  STD_LOGIC
		);
	end component;
	signal tmp_carry : STD_LOGIC;
	begin
		U0:	FullAdder
				port map (A => A(0), B => B(0), CarryIn => CarryIn, S => S(0), CarryOut => tmp_carry); 
		U1:	FullAdder
				port map (A => A(1), B => B(1), CarryIn => tmp_carry, S => S(1), CarryOut => CarryOut);

end Behavioral;

