----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    18:52:44 05/27/2020 
-- Design Name: 
-- Module Name:    Counter3bit - Structural 
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

entity Counter3bit is
	Port ( CE : in  STD_LOGIC;
          CLK : in  STD_LOGIC;
          RST : in  STD_LOGIC;
          Q : out STD_LOGIC_VECTOR (2 downto 0));
end Counter3bit;

architecture Structural of Counter3bit is
	COMPONENT Register_T
		GENERIC (
			WIDTH : POSITIVE 
		);
		PORT(
			CLK : IN std_logic;
			RST : IN std_logic;
			T : IN std_logic_vector(WIDTH-1 downto 0);          
			Q : buffer std_logic_vector(WIDTH-1 downto 0)
		);
	END COMPONENT;
	Signal IQ: STD_LOGIC_VECTOR (2 downto 0);
	Signal T2: STD_LOGIC;
	Signal T3: STD_LOGIC;

begin
	RegisterT1 : Register_T 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			T(0) =>  CE,
			Q(0) => IQ(0)
	);
	RegisterT2 : Register_T 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			T(0) => T2,
			Q(0) => IQ(1)
	);
	
	RegisterT3 : Register_T 
	GENERIC MAP(
		WIDTH => 1
	)
	PORT MAP(
			CLK => CLK,
			RST => RST,
			T(0) => T3,
			Q(0) => IQ(2)
	);
	Q <= IQ;
	T3 <= (CE and IQ(0)) and IQ(1);
	T2 <= CE and IQ(0);
end Structural;

